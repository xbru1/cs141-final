import java.io.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.image.*;

public class Utils {

	// Rectangle helper
	public static Rectangle rectangle(int x, int y, int width, int height) {
		Rectangle r = new Rectangle();
		r.setX(x);
		r.setY(y);
		r.setWidth(width);
		r.setHeight(height);
		Crawler.tiles.getChildren().add(r);
		return r;
	}	

	// Text helper
	public static Text text(String str, int x, int y) {
		Text text = new Text(str);
		text.setX(x);
       		text.setY(y);
       		text.setFocusTraversable(true);
		Crawler.root.getChildren().add(text);
		return text;
	}

	// ImageView helper
	public static ImageView imageView(String path, int x, int y, int width, int height, Group g) throws FileNotFoundException {
		Image image = new Image(new FileInputStream(path));
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setX(x);
		iv.setY(y);
		iv.setFitWidth(width);
		iv.setFitHeight(height);
		g.getChildren().add(iv);
		return iv;
	}

	// Overload with a default group
	public static ImageView imageView(String path, int x, int y, int width, int height) throws FileNotFoundException {
		return imageView(path, x, y, width, height, Crawler.tiles);
	}
}
