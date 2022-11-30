package jobs.Work;


import jobs.Task.baseTask;

import java.util.List;

public abstract class baseJob {
    public int repeats;
    public long startTime;
    public long endTime;
    public List<baseTask> tasks;
}
