package cn.nukkit.ai;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Server;
import cn.nukkit.entity.Entity;

/**
 * @author itsu
 *
 * <h3>AI - Jupiter EntityAI System</h3>
 * <p>一般的なエンティティのAIシステムを提供します。</p>
 *
 * <p>Jupiter Project by JupiterDevelopmentTeam</p>
 *
 */

public class AI {

    private AITask task;

    private List<Entity> entities;
    private Server server;

    /* Constructor */

    public AI(Server server) {
        this.server = server;
    }

    public void initAI() {
        entities = new ArrayList<>();

        this.initAITask();
    }

    @SuppressWarnings("deprecation")
    private void initAITask() {
        task = new AITask(this);
        this.server.getScheduler().scheduleAsyncTask(task);
    }

    public void addAIQueue(Entity entity) {
        entities.add(entity);
    }

    /* Getter */

    public Server getServer() {
        return this.server;
    }

    public List<Entity> getEntitiesQueue() {
        return this.entities;
    }

}
