/*
 * A LivingEntity has various stats that allow it to perform combat with other LivingEntities
 */

import javafx.scene.image.*;
import javafx.scene.*;
import javafx.scene.effect.*;
import java.io.*;
import java.awt.*;

public abstract class LivingEntity extends Entity {

	// Various combat stats
	public int experience;
	protected int maxHpBase;
	public int maxHp;
	public int hp;
	protected int defenseBase;
	public int defense;
	protected int attackBase;
	public int attack;

	// Used to desaturate as this loses health
	// The transient keyword makes this get ignored when serializing
	protected transient ColorAdjust ca;

	public int lastDamage = 0; // Turns since last damage taken

	// Constructor from experience, base stats, and a position
	public LivingEntity(int experience, int maxHpBase, int defenseBase, int attackBase, Vector2 position) {
		this.experience = experience;
		this.maxHpBase = maxHpBase;
		this.defenseBase = defenseBase;
		this.attackBase = attackBase;
		calculateStats();
		this.hp = this.maxHp;
		this.position = position;
		try {
			this.initialize();
		} catch (Exception e) {
			IO.println(e);
		}

	}

	// Constructor from experience and base stats
	public LivingEntity(int experience, int maxHpBase, int defenseBase, int attackBase) {
		this(experience, maxHpBase, defenseBase, attackBase, new Vector2());
	}

	// Constructor with experience alone
	public LivingEntity(int experience) {
		this(experience, 20, 5, 10);;
	}

	// Constructo with nothing, will initialize an Entity with 0 experience
	public LivingEntity() {
		this(0);
	}

	// Recalculate stats based on base stats and the current experience
	public void calculateStats() {
		this.maxHp = (int) ((getLevel() + 5) * maxHpBase * 5 / 100) + 3;
		this.defense = (int) ((getLevel() + 5) * defenseBase * 5 / 100) + 3;
		this.attack = (int) ((getLevel() + 5) * attackBase * 5 / 100) + 3;
		if (this.hp > this.maxHp) {
			this.hp = this.maxHp;
		}
	}

	// Return the current level based on experience
	public int getLevel() {
		return ((int) Math.cbrt(this.experience)) + 5;
	}

	// Code to run on each turn
	// If overriding, always call super.update() AFTER any changes to hp, otherwise the sprite will not properly unrender when the entity is deleted
	public void update() {
		lastDamage++;
		calculateStats();
		if (this.hp <= 0) {
			//Crawler.tiles.getChildren().remove(sprite);
			this.remove();
		}
		ca.setSaturation(-1.0 + ((double) this.hp / this.maxHp));
		super.update();
	}

	// Code to run when removed
	public void remove() {
		Crawler.tiles.getChildren().remove(sprite);
		this.shouldRemove = true;
	}

	// Rendering
	public void render() {
		super.render();
	}

	// Set the current sprite of this Entity
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

	// Attack another entity to reduce their HP
	public void attack(LivingEntity entity) {
		entity.damage(this.attack);
	}

	// Function for receiving damage
	public void damage(int attack) {
		this.hp -= (int) (attack / this.defense) + 3;
		this.lastDamage = 0;
	}

	// Activate an InteractableEntity
	public void getInteractable(InteractableEntity IE) {
		IE.onEnter(this);
	}

	// Code to run once when first loaded
	public void initialize() throws FileNotFoundException {
		this.ca = new ColorAdjust();
	}

	// Conver this to a string
	public String toString() {
		return super.toString() + String.format(", hp:%d/%d (%d base), attack: %d (%d base), defense: %d (%d base), experience: %d, level: %d", this.hp, this.maxHp, this.maxHpBase,this.attack, this.attackBase, this.defense, this.defenseBase, this.experience, this.getLevel());
	}
}
