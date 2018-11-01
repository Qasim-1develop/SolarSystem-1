package entities;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

    private static final float RUN_SPEED = 20;
    private static final float  TURN_SPEED = 160;
    private static final float GRAVITY = 0;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    @Override
    public void setPosition(Vector3f position) {
        super.setPosition(position);
    }

    public void move(Terrain terrain){
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if(super.getPosition().y < terrainHeight){
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = terrainHeight;
        }
    }

    private void jump(){
        if(!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    public void checkInputs(){
//        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
//            this.currentSpeed = RUN_SPEED;
//        }
//        else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
//            this.currentSpeed = -RUN_SPEED;
//        }
//        else {
//            this.currentSpeed = 0;
//        }
//
//        if (Keyboard.isKeyDown(Keyboard.KEY_D)){
//            this.currentTurnSpeed = -TURN_SPEED;
//        }
//        else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
//            this.currentTurnSpeed = TURN_SPEED;
//        }
//        else {
//            this.currentTurnSpeed = 0;
//        }
//
//        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
//            jump();
//        }
        //sun
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            setPosition(new Vector3f(5, 0, -225));
        }
        //mercury
        if(Keyboard.isKeyDown(Keyboard.KEY_1)){
            setPosition(new Vector3f(100, 0, -320));
        }
        //venus
        if(Keyboard.isKeyDown(Keyboard.KEY_2)){
            setPosition(new Vector3f(115, 0, -335));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_3)){
            setPosition(new Vector3f(130, 0, -350));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_4)){
            setPosition(new Vector3f(142, 0, -362));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_5)){
            setPosition(new Vector3f(160, 0, -380));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_6)){
            setPosition(new Vector3f(235, 0, -455));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_7)){
            setPosition(new Vector3f(310, 0, -530));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_8)){
            setPosition(new Vector3f(325, 0, -545));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_9)){
            setPosition(new Vector3f(340, 0, -560));
        }
    }
}
