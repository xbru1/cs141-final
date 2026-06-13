import javafx.scene.image.*;
import javafx.scene.*;
import java.io.*;
import java.awt.*;

public abstract class LivingEntity extends Entity {

	// TODO: Convert stats to getters/setters so that we can hook into them to call calculateStats()
	public int experience;
	protected int maxHpBase = 100;
	public int maxHp;
	public int hp;
	protected int defenseBase = 25;
	public int defense;
	protected int attackBase = 25;
	public int attack;
	public int attackRange;

	public int lastDamage = 0; // Turns since last damage
	public int healDelay = 3; // Turns that must pass before regeneration begins

	public LivingEntity(int experience) {
		this.experience = experience;
		calculateStats();
		this.hp = this.maxHp;
		this.position = new Vector2();
	}

	public LivingEntity(int experience, int maxHp, int defense, int attack, int attackRange) {
		this.experience = experience;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.defense = defense;
		this.attack = attack;
		this.attackRange = attackRange;
		this.position = new Vector2();
	}

	public LivingEntity(int experience, int maxHp, int defense, int attack) {
		this(experience, maxHp, defense, attack, 1);
	}

	public LivingEntity(int experience, int maxHp, int defense) {
		this(experience, maxHp, defense, 10);
	}

	public LivingEntity(int experience, int maxHp) {
		this(experience, maxHp, 5);
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
		
		return super.move(vector);
	}

	// Calculate whether another LivingEntity is within this LivingEntity's attack range using the distance formula
	public boolean canAttack(LivingEntity e) {
		return (Math.abs(Math.sqrt(Math.pow(position.x - e.position.x, 2) + Math.pow(position.y - e.position.y, 2))) <= attackRange);
	}

	// Attack another entity to reduce their HP
	public void attack(LivingEntity e) {
		e.hp -= (int) (this.attack / e.defense) + 3;
		if (Globals.debug) {
			IO.println("Attacked HP: " + e.hp);
		}
	}
}
