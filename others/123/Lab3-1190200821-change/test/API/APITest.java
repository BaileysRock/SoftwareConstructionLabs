package API;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import interval.IntervalConflictException;
import interval.IntervalSet;
import interval.MultiIntervalSet;

public class APITest {
	/**
	 * 测试返回值即可
	 * @throws IntervalConflictException
	 */
   @Test
   public void intervalTolenTest() throws IntervalConflictException {
	   MultiIntervalSet<String> a = MultiIntervalSet.empty();
	   a.insert​(0, 5, "A");
	   a.insert​(20, 25, "A");
	   a.insert​(10, 20, "B");
	   a.insert​(25, 30, "B");
	   assertEquals(30.0, API.intervalTolen(a));
   }
   /**
    * 测试返回值即可
    * @throws IntervalConflictException
    */
   @Test
   public void SimilarityTest() throws IntervalConflictException {
	   MultiIntervalSet<String> a = MultiIntervalSet.empty();
	   MultiIntervalSet<String> b = MultiIntervalSet.empty();
	   a.insert​(0, 5, "A");
	   a.insert​(20, 25, "A");
	   a.insert​(10, 20, "B");
	   a.insert​(25, 30, "B");
	   b.insert​(20, 35, "A");
	   b.insert​(10, 20, "B");
	   b.insert​(0, 5, "C");
	   assertEquals(2.0/7, API.Similarity(a,b));
   }
   /**
    * 测试返回值即可
    * @throws IntervalConflictException
    */
   @Test
   public void calcConflictRatioTest() throws IntervalConflictException {
	   MultiIntervalSet<String> a = MultiIntervalSet.empty();
	   a.insert​(0, 5, "A");
	   a.insert​(0, 5, "B");
	   a.insert​(10, 20, "B");
	   System.out.print(API.calcConflictRatio(a));
	   System.out.println("\n");
	   assertEquals(0.25, API.calcConflictRatio(a));
	   
   }
   /**
    * 测试返回值即可，分为Multi和非Multi
    * @throws IntervalConflictException
    */
   @Test
   public void calcFreeTimeRatioTest() throws IntervalConflictException {
	   MultiIntervalSet<String> a = MultiIntervalSet.empty();
	   IntervalSet<String> b = IntervalSet.empty();
	   a.insert​(0, 5, "A");
	   a.insert​(20, 25, "A");
	   a.insert​(10, 20, "B");
	   a.insert​(25, 30, "B");
	   b.insert(20, 35, "A");
	   b.insert(10, 20, "B");
	   b.insert(0, 5, "C");
	   assertEquals(1.0/6, API.calcFreeTimeRatio(a));
	   assertEquals(1.0/7, API.calcFreeTimeRatio(b));
   }
   
}
