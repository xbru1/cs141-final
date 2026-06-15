import javafx.scene.image.*;
import javafx.scene.*;
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
		this(experience, 20, 5, 20);;
	}
	public LivingEntity() {
		this(0);
	}

	public void calculateStats() {
		int level = ((int) Math.cbrt(this.experience)) + 1;
		this.maxHp = (int) (level * maxHpBase * 50 / 100) + 3;
		this.defense = (int) (level * defenseBase * 50 / 100) + 3;
		this.attack = (int) (level * attackBase * 50 / 100) + 3;
	}

	// Code to run on each turn
	// If overriding via anonymous class, always call super.update() AFTER any changes to hp, otherwise the sprite will not properly unrender when the entity is deleted
	public void update() {
		calculateStats();
		if (this.hp <= 0) {
			Crawler.tiles.getChildren().remove(sprite);
			shouldRemove = true;
		}
		super.update();
	}

	// For now, attacking will involve simply attempting to move onto a tile with a LivingEntity on it
	public boolean move(Vector2 vector) {

		if (World.getEntityAt(new Vector2(this.position.x - vector.x, this.position.y - vector.y)) instanceof LivingEntity) {
			IO.println("Attacking");

			// We have to cast here to tell the compiler that this will in fact be a LivingEntity
			attack((LivingEntity) World.getEntityAt(new Vector2(this.position.x - vector.x, this.position.y - vector.y)));

			// We can't move and attack on the same turn, so we return before calling the super
			return true;
		}
		IO.println("POSITION: " + vector.toString());
		return super.move(vector);
	}

	// Calculate whether another LivingEntity is within this LivingEntity's attack range using the distance formula
	public boolean canAttack(LivingEntity e) {
		return Math.abs(Vector2.distance(this.position, e.position)) <= this.attackRange;
	}

	// Attack another entity to reduce their HP
	public void attack(LivingEntity e) {
		e.hp -= (int) (this.attack / e.defense) + 3;
		if (Globals.debug) {
			IO.println("Attacked HP: " + e.hp);
		}
	}
}
