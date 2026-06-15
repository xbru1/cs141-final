import javafx.scene.image.*;
import javafx.scene.*;
import javafx.scene.effect.*;
import java.io.*;
import java.awt.*;

public abstract class LivingEntity extends Entity {

	// TODO: Convert stats to getters/setters so that we can hook into them to call calculateStats()
	public int experience;
	protected int maxHpBase;
	public int maxHp;
	public int hp;
	protected int defenseBase;
	public int defense;
	protected int attackBase;
	public int attack;
	public int attackRange = 0;

	protected ColorAdjust ca = new ColorAdjust();

	public int lastDamage = 0; // Turns since last damage
	public int healDelay = 3; // Turns that must pass before regeneration begins

	// Constructor from experience
	public LivingEntity(int experience, int maxHpBase, int defenseBase, int attackBase, Vector2 position) {
		this.experience = experience;
		this.maxHpBase = maxHpBase;
		this.defenseBase = defenseBase;
		this.attackBase = attackBase;

		calculateStats();
		this.hp = this.maxHp;
		this.position = position;
	}

	public LivingEntity(int experience, int maxHpBase, int defenseBase, int attackBase) {
		this(experience, maxHpBase, defenseBase, attackBase, new Vector2());
	}

	public LivingEntity(int experience) {
		this(experience, 20, 5, 10);;
	}
	public LivingEntity() {
		this(0);
	}

	// Recalculate stats based on base stats and the current experience
	public void calculateStats() {
		this.maxHp = (int) ((getLevel() + 5) * maxHpBase * 5 / 100) + 3;
		this.defense = (int) ((getLevel() + 5) * defenseBase * 5 / 100) + 3;
		this.attack = (int) ((getLevel() + 5) * attackBase * 5 / 100) + 3;
	}

	public int getLevel() {
		return ((int) Math.cbrt(this.experience)) + 1;
	}

	// Code to run on each turn
	// If overriding, always call super.update() AFTER any changes to hp, otherwise the sprite will not properly unrender when the entity is deleted
	public void update() {
		lastDamage++;
		calculateStats();
		if (this.hp <= 0) {
			Crawler.tiles.getChildren().remove(sprite);
			shouldRemove = true;
		}
		ca.setSaturation(-1.0 + ((double) this.hp / this.maxHp));
		super.update();
	}

	public void render() {
		super.render();
	}

	public void setSprite(String path, Group g) throws FileNotFoundException {
		super.setSprite(path, g);
		this.sprite.setEffect(ca);
	}


	// For now, attacking will involve simply attempting to move onto a tile with a LivingEntity on it
	public boolean move(Vector2 vector) {

		if (World.getLivingEntityAt(Vector2.difference(position, vector)) != null) {
			// We have to cast here to tell the compiler that this will in fact be a LivingEntity
			attack((LivingEntity) World.getLivingEntityAt(new Vector2(this.position.x - vector.x, this.position.y - vector.y)));

			// We can't move and attack on the same turn, so we return before calling the super
			return true;
		}
		return super.move(vector);
	}

	// Calculate whether another LivingEntity is within this LivingEntity's attack range using the distance formula
	public boolean canAttack(LivingEntity e) {
		return Math.abs(Vector2.distance(this.position, e.position)) <= this.attackRange;
	}

	// Attack another entity to reduce their HP
	public void attack(LivingEntity e) {
		e.damage(this.attack);
		if (Globals.debug) {
			IO.println("Attacked HP: " + e.hp);
		}
	}

	// Function for receiving damage
	public void damage(int attack) {
		this.hp -= (int) (attack / this.defense) + 3;
		this.lastDamage = 0;
	}

	public void getItem(Item item) {
		item.pickup(this);
	}
}
