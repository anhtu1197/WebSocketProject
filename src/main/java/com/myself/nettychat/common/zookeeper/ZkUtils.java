package com.myself.nettychat.common.zookeeper;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Data
@NoArgsConstructor
public class ZkUtils {

    private CuratorFramework zkClient = null;

    ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    List<PathChildrenCache> pathChildrenCaches = new CopyOnWriteArrayList<>();

    List<NodeCache> nodeCaches = new CopyOnWriteArrayList<>();

    List<TreeCache> treeCaches = new CopyOnWriteArrayList<>();


    public void init(String zookeeperServer,
                     int connectionTimeout,
                     int sessionTimeout,
                     int maxRetries, int retriesSleepTime,
                     String namespace,
                     ZkStateListener listener) {
        if (zkClient == null) {
            zkClient = CuratorFrameworkFactory.builder()
                    .connectString(zookeeperServer)
                    .connectionTimeoutMs(connectionTimeout)
                    .sessionTimeoutMs(sessionTimeout)
                    .namespace(namespace)
                    .retryPolicy(new RetryNTimes(maxRetries, retriesSleepTime))
                    .build();
        }
        zkClient.getConnectionStateListenable().addListener((curatorFramework, connectionState) -> {
            pathChildrenCaches.clear();
            nodeCaches.clear();
            treeCaches.clear();
            if (connectionState == ConnectionState.CONNECTED) {
                listener.connectedEvent(curatorFramework, connectionState);
            } else if (connectionState == ConnectionState.RECONNECTED) {
                listener.ReconnectedEvent(curatorFramework, connectionState);
            } else if (connectionState == ConnectionState.LOST) {
                listener.lostEvent(curatorFramework, connectionState);
            }
        });
        zkClient.start();
    }

    public void destory() {
        pathChildrenCaches.stream().forEach(cache -> CloseableUtils.closeQuietly(cache));
        pathChildrenCaches.clear();
        pathChildrenCaches = null;
        nodeCaches.stream().forEach(cache -> CloseableUtils.closeQuietly(cache));
        nodeCaches.clear();
        nodeCaches = null;
        treeCaches.stream().forEach(cache -> CloseableUtils.closeQuietly(cache));
        treeCaches.clear();
        treeCaches = null;
        if (zkClient != null) {
            CloseableUtils.closeQuietly(zkClient);
        }
    }



    public boolean createNode(String path, String data, CreateMode mode) {
        if (!ObjectUtils.allNotNull(zkClient, path)) {
            return Boolean.FALSE;
        }
        try {
            Stat stat = exists(path);
            if (stat == null) {
                mode = mode == null ? CreateMode.PERSISTENT : mode;
                String opResult;
                if (ObjectUtils.allNotNull(data)) {
                    opResult = zkClient.create().creatingParentContainersIfNeeded().withMode(mode).forPath(path, data.getBytes(Charsets.UTF_8));
                } else {
                    opResult = zkClient.create().creatingParentContainersIfNeeded().withMode(mode).forPath(path);
                }
                return Objects.equal(opResult, path);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("create node fail! path: {}, error: {}", path, e);
        }
        return Boolean.FALSE;
    }



    public boolean deleteNode(String path, Integer version) {
        if (!ObjectUtils.allNotNull(zkClient, path)) {
            return Boolean.FALSE;
        }
        try {
            Stat stat = exists(path);
            if (stat != null) {
                if (version == null) {
                    zkClient.delete().deletingChildrenIfNeeded().forPath(path);
                } else {
                    zkClient.delete().deletingChildrenIfNeeded().withVersion(version).forPath(path);
                }
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("delete node fail! path: {}, error: {}", path, e);
        }
        return Boolean.FALSE;
    }


    public boolean deleteNode(String path) {
        return deleteNode(path, null);
    }


    public byte[] getNodeData(String path) {
        if (!ObjectUtils.allNotNull(zkClient, path)) {
            return null;
        }
        try {
            Stat stat = exists(path);
            if (stat != null) {
                return zkClient.getData().forPath(path);
            }
        } catch (Exception e) {
            log.error("get node data fail! path: {}, error: {}", path, e);
        }
        return null;
    }


    public String getNodeDataStr(String path) {
        byte[] val = getNodeData(path);
        return val == null ? null : new String(val, Charsets.UTF_8);
    }

    public Stat exists(String path) {
        if (!ObjectUtils.allNotNull(zkClient, path)) {
            return null;
        }
        try {
            return zkClient.checkExists().forPath(path);
        } catch (Exception e) {
            log.error("check node exists fail! path: {}, error: {}", path, e);
        }
        return null;
    }


    public boolean checkExists(String path) {
        return exists(path) == null ? Boolean.FALSE : Boolean.TRUE;
    }


    public boolean setNodeData(String path, String data) {
        return setNodeData(path, data.getBytes(Charsets.UTF_8), null);
    }


    public boolean setNodeData(String path, byte[] data, Integer version) {
        if (!ObjectUtils.allNotNull(zkClient, path)) {
            return Boolean.FALSE;
        }
        try {
            Stat stat = exists(path);
            if (stat != null) {
                if (version == null) {
                    zkClient.setData().forPath(path, data);
                } else {
                    zkClient.setData().withVersion(version).forPath(path, data);
                }
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error("set node data fail! path: {}, error: {}", path, e);
        }
        return Boolean.FALSE;
    }


    public boolean listenerPathChildrenCache(String path, BiConsumer<CuratorFramework, PathChildrenCacheEvent> biConsumer) {

        if (!ObjectUtils.allNotNull(zkClient, path, biConsumer)) {
            return Boolean.FALSE;
        }
        try {
            Stat stat = exists(path);
            if (stat != null) {
                PathChildrenCache watcher = new PathChildrenCache(zkClient, path, true);
                watcher.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
                //该模式下 watcher在重连的时候会自动 rebuild
                watcher.getListenable().addListener(biConsumer::accept, pool);
                if (!pathChildrenCaches.contains(watcher)) {
                    pathChildrenCaches.add(watcher);
                }
//                else{
//                    watcher.rebuild();
//                }
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error("listen path children cache fail! path:{} , error:{}", path, e);
        }
        return Boolean.FALSE;
    }



    public Map<String, String> readTargetChildsData(String path) {
        if (!ObjectUtils.allNotNull(zkClient, path)) {
            return null;
        }
        Map<String, String> map = null;
        try {
            Stat stat = exists(path);
            if (stat != null) {
                List<String> childrens = zkClient.getChildren().forPath(path);
                GetDataBuilder dataBuilder = zkClient.getData();
                if (childrens != null) {
                    map = childrens.stream().collect(Collectors.toMap(Function.identity(), (child) -> {
                        try {
                            return new String(dataBuilder.forPath(ZKPaths.makePath(path, child)), Charsets.UTF_8);
                        } catch (Exception e1) {
                            return null;
                        }
                    }));
                }
            }
        } catch (Exception e) {
            log.error("get target childs data fail!, path:{} , error:{}", path, e);
        }
        return map;

    }


    public static void main(String[] a) throws Exception {
        ZkUtils zkUtils = new ZkUtils();
        zkUtils.init("127.0.0.1:2181", 1000, 2000, 5, 1000, "test", new ZkStateListener() {
            @Override
            public void connectedEvent(CuratorFramework curator, ConnectionState state) {

            }

            @Override
            public void ReconnectedEvent(CuratorFramework curator, ConnectionState state) {

            }

            @Override
            public void lostEvent(CuratorFramework curator, ConnectionState state) {

            }
        });
        zkUtils.getZkClient().create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/yy/uu/ii");

        Thread.sleep(Long.MAX_VALUE);
    }

}
