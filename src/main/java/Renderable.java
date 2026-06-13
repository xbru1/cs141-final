import javafx.scene.*;
import javafx.scene.image.*;
import java.io.*;

interface Renderable {
	public void setSprite(String path, Group g) throws FileNotFoundException;
	public void render();
}
