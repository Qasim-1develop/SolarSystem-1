package engineTester;

import entities.*;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.w3c.dom.Text;
import renderEngine.*;
import models.RawModel;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        // TERRAIN TEXTURE CODE
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("road"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        //////////////////////////////////////////////////////////////////////////////////////

        ModelData data = OBJFileLoader.loadOBJ("Car");
        RawModel carModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());

        TexturedModel staticModel = new TexturedModel(OBJLoader.loadObjModel("Car", loader), new ModelTexture(loader.loadTexture("futuristicCarTexture")));

        TexturedModel plutoModel = new TexturedModel(OBJLoader.loadObjModel("pluto", loader), new ModelTexture(loader.loadTexture("plutoTexture")));
        TexturedModel neptuneModel = new TexturedModel(OBJLoader.loadObjModel("neptune", loader), new ModelTexture(loader.loadTexture("neptuneTexture")));
        TexturedModel uranusModel = new TexturedModel(OBJLoader.loadObjModel("uranus", loader), new ModelTexture(loader.loadTexture("uranusTexture")));
        TexturedModel saturnRingModel = new TexturedModel(OBJLoader.loadObjModel("saturnRing", loader), new ModelTexture(loader.loadTexture("saturnRingTexture")));
        TexturedModel saturnModel = new TexturedModel(OBJLoader.loadObjModel("saturn", loader), new ModelTexture(loader.loadTexture("saturnTexture")));
        TexturedModel moonModel = new TexturedModel(OBJLoader.loadObjModel("moon", loader), new ModelTexture(loader.loadTexture("moonTexture")));
        TexturedModel jupiterModel = new TexturedModel(OBJLoader.loadObjModel("jupiter", loader), new ModelTexture(loader.loadTexture("jupiterTexture")));
        TexturedModel marsModel = new TexturedModel(OBJLoader.loadObjModel("mars", loader), new ModelTexture(loader.loadTexture("marsTexture")));
        TexturedModel earthModel = new TexturedModel(OBJLoader.loadObjModel("earth", loader), new ModelTexture(loader.loadTexture("earthTexture")));
        TexturedModel venusModel = new TexturedModel(OBJLoader.loadObjModel("venus", loader), new ModelTexture(loader.loadTexture("venusTexture")));
        TexturedModel mercuryModel = new TexturedModel(OBJLoader.loadObjModel("mercury", loader), new ModelTexture(loader.loadTexture("mercuryTexture")));
        TexturedModel sunModel = new TexturedModel(OBJLoader.loadObjModel("sun", loader), new ModelTexture(loader.loadTexture("sunTexture")));

        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        //Entity entity = new Entity(staticModel, new Vector3f(0, 0, -5), 0, 45, 0, 1);
        Planet pluto = new Planet(plutoModel, new Vector3f(340, 5, -560), 0, 0, 0, 0.5f);
        Planet neptune = new Planet(neptuneModel, new Vector3f(325, 5, -545), 0, 0, 0, 0.7f);
        Planet uranus = new Planet(uranusModel, new Vector3f(310, 5, -530), 0, 0, 0, 0.7f);
        Planet moon2 = new Planet(moonModel, new Vector3f(190, 5, -452.5f), 0, 0, 0, 0.2f);
        Planet saturnRing = new Planet(saturnRingModel, new Vector3f(235, 3, -455), 0, 0, 0, 1f);
        Planet saturn = new Planet(saturnModel, new Vector3f(235, 5, -455), 0, 0, 0, 1f);
        Planet moon = new Planet(moonModel, new Vector3f(205, 5, -452.5f), 0, 0, 0, 0.2f);
        Planet jupiter = new Planet(jupiterModel, new Vector3f(160, 5, -380), 0, 5, 0, 1.5f);
        Planet mars = new Planet(marsModel, new Vector3f(142, 5, -362), 0, 5, 0, 0.7f);
        Planet earth = new Planet(earthModel, new Vector3f(130, 5, -350), 0, 0, 0, 0.8f);
        Planet venus = new Planet(venusModel, new Vector3f(115, 5, -335), 0, 5, 0, 0.5f);
        Planet mercury = new Planet(mercuryModel, new Vector3f(100, 5, -320), 0, 5, 0, 0.5f);
        Planet sun = new Planet(sunModel, new Vector3f(5, 5, -225), 0, 5, 0, 10f);

//        List<Planet> planets = new ArrayList<Planet>();
//        planets.add(saturn);
//        planets.add(moon);
//        planets.add(jupiter);
//        planets.add(mars);


        List<Entity> entities = new ArrayList<Entity>();
        entities.add(pluto);
        entities.add(neptune);
        entities.add(uranus);
        entities.add(moon2);
        entities.add(saturnRing);
        entities.add(saturn);
        entities.add(moon);
        entities.add(jupiter);
        entities.add(mars);
        entities.add(earth);
        entities.add(venus);
        entities.add(mercury);
        entities.add(sun);

        List<Entity> normalMapEntities = new ArrayList<Entity>();
        TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader), new ModelTexture(loader.loadTexture("barrel")));

        barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
        barrelModel.getTexture().setShineDamper(10);
        barrelModel.getTexture().setReflectivity(0.5f);

        //normalMapEntities.add(new Entity(barrelModel, new Vector3f(235, 7, -455), 0, 0, 0, 1f));

        Light sunlight = new Light(new Vector3f(10, 15, -10), new Vector3f(1.3f, 1.3f, 1.3f));
        List<Light> lights = new ArrayList<Light>();
//        lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));
//        lights.add(new Light(new Vector3f(200, 10, -190), new Vector3f(10, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
//        lights.add(new Light(new Vector3f(210, 14, -210), new Vector3f(0, 0, 10), new Vector3f(1, 0.01f, 0.002f)));

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        List<Terrain> terrains = new ArrayList<Terrain>();
        //terrains.add(terrain);
        //Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");

        Player player = new Player(staticModel, new Vector3f(230, 5, -450), 0, 0, 0, 1);
        //entities.add(player);
        Camera camera = new Camera(player);

        MasterRenderer renderer = new MasterRenderer(loader, camera);

        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("brick"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        //guis.add(gui);


        GuiRenderer guiRenderer = new GuiRenderer(loader);

        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);

        GuiTexture shadowMap = new GuiTexture(renderer.getShadowMapTexture(), new Vector2f(0.5f, 0.5f), new Vector2f(0.5f, 0.5f));
        //guis.add(shadowMap);

        WaterFrameBuffers buffers = new WaterFrameBuffers();
        WaterShader waterShader = new WaterShader();
        WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
        List<WaterTile> waters = new ArrayList<WaterTile>();
        WaterTile water = new WaterTile(235, -455, -5);
        waters.add(water);

        while(!Display.isCloseRequested()){
            // game logic
            pluto.orbit(0, 10);
            neptune.orbit(0, 10);
            uranus.orbit(0, 10);
            moon2.orbit(-20.005f, -20.5f);
            saturnRing.orbit(0, 10);
            saturn.orbit(0, 10);
            moon.orbit(20.005f, 32.5f);
            jupiter.orbit(0, 10);
            mars.orbit(0, 10);
            earth.orbit(0, 10);
            venus.orbit(0, 10);
            mercury.orbit(0, 10);

            player.move(terrain);
            //System.out.println(moon.getPosition());
            camera.move();
            System.out.println(camera.getPosition());


//            picker.update();
//            Vector3f terrainPoint = picker.getCurrentTerrainPoint();
//            if(terrainPoint != null){
//                //set entity position
//            }
//            System.out.println(picker.getCurrentRay());

            //renderer.renderScene(entities,terrain, lights, camera);
            renderer.renderShadowMap(entities, sunlight);
            GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

            // render reflection texture
            buffers.bindReflectionFrameBuffer();
            float distance = 2 * (camera.getPosition().y - water.getHeight());
            camera.getPosition().y -= distance;
            camera.invertPitch();
//            renderer.processEntity(player);
//            renderer.processTerrain(terrain);
//            renderer.render(lights, camera, new Vector4f(0, 1, 0, water.getHeight())); // be careful with +1f offset. Might cause incorrect reflections. Disabled for now
            renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, water.getHeight()));
            camera.getPosition().y += distance;
            camera.invertPitch();

            // render refraction texture
            buffers.bindRefractionFrameBuffer();
//            renderer.processEntity(player);
//            renderer.processTerrain(terrain);
//            renderer.render(lights, camera, new Vector4f(0, -1, 0, 15));
            renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));
            buffers.unbindCurrentFrameBuffer();

            //renderer.processTerrain(terrain2);
            //renderer.processEntity(entity);
//            renderer.processEntity(player);
//            renderer.processTerrain(terrain);
//            renderer.render(lights, camera, new Vector4f(0, -1, 0, 15));
            renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 120));
            //waterRenderer.render(waters, camera, sun);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }

        buffers.cleanUp();
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
