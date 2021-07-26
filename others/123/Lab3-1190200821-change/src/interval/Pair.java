package interval;
/**
 * 
 * @author liyu
 *�Զ���ʱ���adt
 * @param <K> start
 * @param <V> end
 */
public class Pair<K, V> {
   private K key ;
   private V value;
   public Pair(K key,V value) {
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
   /**
    * ��дhashCode��equal����
    */
   @Override
	public boolean equals(Object obj) {
	  return this.hashCode() == obj.hashCode();
   }
   @Override
   public int hashCode() {
	   return this.key.hashCode() * this.value.hashCode();
   }
   
}
