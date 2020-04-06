package sample.Entity;

import javafx.scene.canvas.GraphicsContext;
import sample.Tools.RunLater;
;

import java.util.ArrayList;

public class EntityLayer {
    private ArrayList<Entity> entities;
    private GraphicsContext gameDraw;
    private ArrayList<RunLater> addLater;

    public EntityLayer(GraphicsContext gameDraw) {
        this.gameDraw = gameDraw;
        entities = new ArrayList<>();
        addLater = new ArrayList<>();
    }

    public ArrayList<RunLater> getAddLater() {
        return addLater;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Entity getEntity() {
        if (entities.isEmpty()) return null;
        return entities.get(entities.size()-1);
    }

    public void update(double dt) {
        for (Entity i:entities) {
            i.update(dt);
        }
        for (RunLater i : addLater) {
            entities.add((Entity) i.obj);
        }
        addLater.clear();
    }

    public void render() {
        if (!isEmpty())
        getEntity().render(gameDraw);
    }

    public boolean isEmpty() {
        return entities.isEmpty();
    }


}
