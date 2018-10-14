package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
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
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        //Entity entity = new Entity(staticModel, new Vector3f(0, 0, -5), 0, 45, 0, 1);
        //List<Entity> entities = new ArrayList<Entity>();
        //entities.add(new Entity())
        Light light = new Light(new Vector3f(0, 0, -10), new Vector3f(1, 1, 1));
        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));
        lights.add(new Light(new Vector3f(200, 10, -190), new Vector3f(10, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(210, 14, -210), new Vector3f(0, 0, 10), new Vector3f(1, 0.01f, 0.002f)));

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        //Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");

        MasterRenderer renderer = new MasterRenderer(loader);

        Player player = new Player(staticModel, new Vector3f(195, 5, -409), 0, 0, 0, 1);
        Camera camera = new Camera(player);

        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("brick"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        //guis.add(gui);


        GuiRenderer guiRenderer = new GuiRenderer(loader);

        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);

        WaterFrameBuffers buffers = new WaterFrameBuffers();
        WaterShader waterShader = new WaterShader();
        WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
        List<WaterTile> waters = new ArrayList<WaterTile>();
        WaterTile water = new WaterTile(235, -455, -5);
        waters.add(water);

        while(!Display.isCloseRequested()){
            // game logic
            //entity.increaseRotation(0, 0.1f, 0);
            player.move(terrain);
            System.out.println(player.getPosition());
            camera.move();


//            picker.update();
//            Vector3f terrainPoint = picker.getCurrentTerrainPoint();
//            if(terrainPoint != null){
//                //set entity position
//            }
//            System.out.println(picker.getCurrentRay());

            //renderer.renderScene(entities,terrain, lights, camera);
            GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

            // render reflection texture
            buffers.bindReflectionFrameBuffer();
            float distance = 2 * (camera.getPosition().y - water.getHeight());
            camera.getPosition().y -= distance;
            camera.invertPitch();
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            renderer.render(lights, camera, new Vector4f(0, 1, 0, water.getHeight())); // be careful with +1f offset. Might cause incorrect reflections. Disabled for now
            camera.getPosition().y += distance;
            camera.invertPitch();

            // render refraction texture
            buffers.bindRefractionFrameBuffer();
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            renderer.render(lights, camera, new Vector4f(0, -1, 0, 15));
            buffers.unbindCurrentFrameBuffer();

            //renderer.processTerrain(terrain2);
            //renderer.processEntity(entity);
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            renderer.render(lights, camera, new Vector4f(0, -1, 0, 15));
            waterRenderer.render(waters, camera, light);
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
