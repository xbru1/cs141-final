import java.io.*;

public class Enemy extends LivingEntity implements Updateable {

	public Enemy() throws FileNotFoundException {
		super();
		setSprite("./assets/enemy.png", Crawler.tiles);
	}

	public void update() {
		IO.println(this.hp);
		super.update();
	}
}
