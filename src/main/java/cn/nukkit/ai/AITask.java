package cn.nukkit.ai;

import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;

/**
 * @author itsu
 *
 * <h3>AITask - Jupiter EntityAI System</h3>
 * <p>EntityAIシステムにかかわる非同期のタスククラスです。</p>
 *
 * <p>Jupiter Project by JupiterDevelopmentTeam</p>
 *
 * @see AI
 *
 */

public class AITask extends AsyncTask {

    private AI ai;
    private Server server;

    public AITask(AI ai) {
        this.ai = ai;
        this.server = ai.getServer();
    }

    @Override
    public void onRun() {
        while(server.isRunning()) {
            tick();
        }
    }

    public void tick() {

    }

}
