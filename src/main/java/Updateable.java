/*
 * Interface that guarantees a method that can be run on each turn and when the object should be removed
 */

public interface Updateable {
	public void update();
	public void remove();
}
