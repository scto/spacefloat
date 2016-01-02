package me.fahien.spacefloat.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import org.junit.Before;
import org.junit.Test;

import me.fahien.spacefloat.component.AccelerationComponent;
import me.fahien.spacefloat.component.CollisionComponent;
import me.fahien.spacefloat.component.EnergyComponent;
import me.fahien.spacefloat.component.GraphicComponent;
import me.fahien.spacefloat.component.PlayerComponent;
import me.fahien.spacefloat.component.ReactorComponent;
import me.fahien.spacefloat.component.TransformComponent;
import me.fahien.spacefloat.component.VelocityComponent;
import me.fahien.spacefloat.entity.GameObject;
import me.fahien.spacefloat.component.GravityComponent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * The {@link GameObjectService} Test Case
 *
 * @author Fahien
 */
public class GameObjectServiceTest {
	private static final String SPACESHIP_NAME = "Spaceship";
	private static final String EARTH_NAME = "Earth";
	private static final String MODEL_NAME = "cargo.g3db";
	private static final String REACTOR_NAME = "reactor.pfx";
	private static final float RADIUS_COLLISION = 50.0f;

	private GameObjectService factory;

	@Before
	public void before() {
		factory = new GameObjectService();
	}

	@Test
	public void canSaveTheSpaceship() {
		GameObject spaceship = new GameObject();
		spaceship.setName(SPACESHIP_NAME);
		GraphicComponent graphic = new GraphicComponent();
		graphic.setName(MODEL_NAME);
		spaceship.add(graphic);
		TransformComponent position = new TransformComponent();
		spaceship.add(position);
		VelocityComponent velocity = new VelocityComponent();
		spaceship.add(velocity);
		AccelerationComponent acceleration = new AccelerationComponent();
		spaceship.add(acceleration);
		PlayerComponent player = new PlayerComponent();
		spaceship.add(player);
		CollisionComponent collision = new CollisionComponent(RADIUS_COLLISION);
		spaceship.add(collision);
		ReactorComponent reactorComponent = new ReactorComponent();
		reactorComponent.setName(REACTOR_NAME);
		spaceship.add(reactorComponent);
		EnergyComponent energy = new EnergyComponent();
		spaceship.add(energy);
		factory.save(spaceship);
	}

	@Test
	public void canLoadTheSpaceship() {
		GameObject spaceship = factory.load(SPACESHIP_NAME);
		assertEquals("The name is not equals to " + SPACESHIP_NAME, SPACESHIP_NAME, spaceship.getName());
		GraphicComponent graphic = spaceship.getComponent(GraphicComponent.class);
		assertNotNull("The spaceship has no graphic component", graphic);
		assertEquals("The graphic name is not equals to " + MODEL_NAME, MODEL_NAME, graphic.getName());
		TransformComponent position = spaceship.getComponent(TransformComponent.class);
		assertNotNull("The spaceship has no position component", position);
		VelocityComponent velocity = spaceship.getComponent(VelocityComponent.class);
		assertNotNull("The spaceship has no velocity component", velocity);
		PlayerComponent player = spaceship.getComponent(PlayerComponent.class);
		assertNotNull("The spaceship has no player component", player);
		AccelerationComponent acceleration = spaceship.getComponent(AccelerationComponent.class);
		assertNotNull("The spaceship has no acceleration component", acceleration);
		CollisionComponent collision = spaceship.getComponent(CollisionComponent.class);
		assertNotNull("The spaceship has no collision component", collision);
		ReactorComponent reactor = spaceship.getComponent(ReactorComponent.class);
		assertNotNull("The spaceship has no reactor component", reactor);
		EnergyComponent energy = spaceship.getComponent(EnergyComponent.class);
		assertNotNull("The spaceship has no energy component", energy);
	}

	@Test
	public void canLoadTheEarth() {
		GameObject earth = factory.load(EARTH_NAME);
		assertEquals("The name is not equals to " + EARTH_NAME, EARTH_NAME, earth.getName());
		GravityComponent gravity = earth.getComponent(GravityComponent.class);
		assertNotNull("The spaceship has no gravity component", gravity);
	}

	/**
	 * Creates the {@link GameObject}s list
	 */
	private void createObjectList() {
		String objectList = "";
		FileHandle[] files = Gdx.files.local(GameObjectService.OBJECTS_DIR).list();
		for (FileHandle file : files) {
			if (file.path().endsWith(GameObjectService.JSON_EXT)) {
				objectList += file.nameWithoutExtension() + "\n";
			}
		}
		FileHandle fileList = Gdx.files.local(GameObjectService.OBJECT_LIST);
		fileList.writeString(objectList, false);
	}

	@Test
	public void canLoadAllObjects() {
		createObjectList();
		Array<GameObject> objects = factory.loadObjects();
		assertNotNull("Objects array is null", objects);
		assertTrue("The array does not contain any object", objects.size > 0);
	}

	@Test
	public void canSaveAllObjects() {
		createObjectList();
		Array<GameObject> objects = factory.loadObjects();
		factory.saveObjects(objects);
		objects = factory.loadObjects();
		assertNotNull("Objects array is null", objects);
		assertTrue("The array does not contain any object", objects.size > 0);
	}
}
