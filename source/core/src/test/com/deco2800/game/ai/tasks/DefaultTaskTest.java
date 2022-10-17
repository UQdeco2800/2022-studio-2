package com.deco2800.game.ai.tasks;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

import com.deco2800.game.ai.tasks.Task.Status;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
class DefaultTaskTest {
  @Test
  void shouldHaveCorrectStatus() {
    DefaultTask task = spy(DefaultTask.class);
    Entity entity = new Entity();
    assertEquals(Status.INACTIVE, task.getStatus());

    task.start();
    assertEquals(Status.ACTIVE, task.getStatus());

    task.stop();
    assertEquals(Status.INACTIVE, task.getStatus());
  }

  @Test
  void updateTest() {
    DefaultTask task = spy(DefaultTask.class);
    task.update();
  }
}