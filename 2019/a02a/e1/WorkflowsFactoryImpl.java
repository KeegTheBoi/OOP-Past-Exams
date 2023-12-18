package a02a.e1;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorkflowsFactoryImpl implements WorkflowsFactory {

    @Override
    public <T> Workflow<T> singleTask(T task) {
        return tasksSequence(new ArrayList<>(List.of(task)));
    }

    @Override
    public <T> Workflow<T> tasksSequence(List<T> tasks) {
        return workList(tasks);
    }

    @Override
    public <T> Workflow<T> tasksJoin(Set<T> initialTasks, T finalTask) {
        return concat(tasksSequence(new ArrayList<>(initialTasks)), singleTask(finalTask));
    }

    @Override
    public <T> Workflow<T> tasksFork(T initialTask, Set<T> finalTasks) {
        return concat(singleTask(initialTask), tasksSequence(new ArrayList<>(finalTasks)));
    }

    @Override
    public <T> Workflow<T> concat(Workflow<T> first, Workflow<T> second) {
        return new Workflow<T>() {

            Set<T> firstTasks = first.getTasks();
            Set<T> secondTasks = second.getTasks();

            @Override
            public Set<T> getTasks() {
                return Stream.concat(first.getTasks().stream(), second.getTasks().stream()).collect(Collectors.toSet());
            }

            @Override
            public Set<T> getNextTasksToDo() {
                return !firstTasks.isEmpty() ? firstTasks : secondTasks;
            }

            @Override
            public void doTask(T t) {
                if(!first.isCompleted()) {
                    first.doTask(t);
                    firstTasks.remove(t);
                } else {
                   second.doTask(t);
                    secondTasks.remove(t);
                }
            }

            @Override
            public boolean isCompleted() {
                return first.isCompleted() && second.isCompleted();
            }
            
        };
    }

    public <T> Workflow<T> workList(List<T> tasks) {
        return new Workflow<T>() {

            Deque<T> nextTask = new ArrayDeque<>(tasks);
            @Override
            public Set<T> getTasks() {
                return tasks.stream().collect(Collectors.toSet());
            }

            @Override
            public Set<T> getNextTasksToDo() {
                if(nextTask.isEmpty()) {
                    return Collections.emptySet();
                }
                return Set.of(nextTask.getFirst());
            }

            @Override
            public void doTask(T t) {
                if(!getTasks().contains(t)) {
                    throw new IllegalArgumentException();
                }
                if(!nextTask.getFirst().equals(t)) {
                    throw new IllegalStateException();
                }
                nextTask.remove(t);
            }

            @Override
            public boolean isCompleted() {
                return nextTask.isEmpty();
            }
            
        };
    }

}
