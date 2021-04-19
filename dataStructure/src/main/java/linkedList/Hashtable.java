package linkedList;

public class Hashtable<K, V>{

    //散列表默认长度
    private static final int DEFAULT_INITAL_CAPACITY = 8;

    //装载因子
    private static final float LOAD_FACTOR = 0.75f;

    //初始化数组
    private Entry<K, V>[] table;

    //实际数据数量
    private int size = 0;

    //使用散列表数量
    private int use = 0;

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public Hashtable() {
        table = (Entry<K, V>[]) new Entry[DEFAULT_INITAL_CAPACITY];
    }

    public void put(K key, V value){
        int hash = hash(key);
        if(table[hash] == null)
            table[hash] = new Entry<K, V>(null, null, null);

        Entry<K, V> temp = table[hash];
        if(temp.next == null){
            temp.next = new Entry<K, V>(key, value, null);
            size++;
            use++;
            if(size >= LOAD_FACTOR*table.length){
                resize();
            }
        }else{
            do{
                temp = temp.next;
                if(temp.key == key){
                    temp.value = value;
                    return;
                }
            }while(temp.next!=null);
            Entry tep = table[hash].next;
            table[hash].next = new Entry<K, V>(key, value, tep);
            size++;
        }
    }

    public int hash(Object key){
        int h;
        return (key == null) ? 0 :((h = key.hashCode()) ^ (h >>> 16)) % table.length;
    }

    public void resize(){
        Entry<K, V>[] oldTable = table;
        table = new Entry[table.length * 2];
        use = 0;
        for(int i = 0; i<oldTable.length; i++){
            if(oldTable[i] == null || oldTable[i].next == null)
                continue;
            Entry<K, V> e = oldTable[i];
            while(e.next != null){
                e = e.next;
                int hash = hash(e.key);
                if(table[hash] == null){
                    use++;
                    table[hash] = new Entry<K, V>(null, null, null);
                }
                table[hash].next = new Entry<K, V>(e.key, e.value, table[hash].next);
            }
        }
    }

    public V get(K key){
        int hash = hash(key);
        Entry<K, V> e = table[hash];
        if(e == null || e.next == null)
            return null;

        while(e.next != null){
            e = e.next;
            if(e.key.equals(key)){
                return e.value;
            }
        }
        return null;
    }

    public void del(K key){
        int hash = hash(key);
        Entry<K, V> e = table[hash];
        if(e == null || e.next == null)
            return;

        Entry<K, V> prev;
        Entry<K, V> object = table[hash];
        do{
            prev = e;
            e = e.next;
            if(e.key.equals(key)){
                prev.next = e.next;
                if(object.next == null) use--;
                size--;
                return;
            }
        }while (e.next != null);
    }
}
