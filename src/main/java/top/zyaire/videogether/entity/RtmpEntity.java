package top.zyaire.videogether.entity;

public class RtmpEntity <K,V>{
    private K key;
    private V value;

    public RtmpEntity(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public RtmpEntity() {
    }

    public void put(K key ,V value){
        this.key = key;
        this.value = value;
    }
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
