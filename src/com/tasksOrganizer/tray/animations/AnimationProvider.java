package com.tasksOrganizer.tray.animations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnimationProvider {
    private List<TrayAnimation> animationsList = new ArrayList();

    public AnimationProvider(TrayAnimation... animations) {
        Collections.addAll(this.animationsList, animations);
    }

    public void addAll(TrayAnimation... animations) {
        Collections.addAll(this.animationsList, animations);
    }

    public TrayAnimation get(int index) {
        return (TrayAnimation)this.animationsList.get(index);
    }

    public TrayAnimation findFirstWhere(Predicate<? super TrayAnimation> predicate) {
        return (TrayAnimation)this.animationsList.stream().filter(predicate).findFirst().orElse(null);
    }

    public List<TrayAnimation> where(Predicate<? super TrayAnimation> predicate) {
        return (List)this.animationsList.stream().filter(predicate).collect(Collectors.toList());
    }
}
