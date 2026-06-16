import java.io.*;
import java.util.*;

public abstract class InteractableEntity extends Entity {

	public InteractableEntity() throws FileNotFoundException {
		super();
	}

	public void onEnter(LivingEntity entity) {
		this.remove();
	}
}
