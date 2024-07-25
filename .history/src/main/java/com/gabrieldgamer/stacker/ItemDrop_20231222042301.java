package com.gabrieldgamer.stacker;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class ItemDrop {

    public void exemploStack(Entity entidade) {
        // Verificar se a entidade pode ser empilhada
        if (WildStackerAPI.canBeStacked(entidade)) {
            // Empilhar a entidade com outra próxima
            WildStackerAPI.stackEntities(entidade);
        }
    }

    public void exemploUnstack(Entity entidade) {
        // Desempilhar a entidade
        WildStackerAPI.unstackEntity(entidade);
    }

    public void exemploObterTamanhoEmpilhamento(Entity entidade) {
        // Obter o tamanho do empilhamento ao qual a entidade pertence
        int tamanhoEmpilhamento = WildStackerAPI.getStackSize(entidade);
    }

    public void exemploVerificarSeEmpilhado(Entity entidade) {
        // Verificar se a entidade está empilhada
        boolean estaEmpilhado = WildStackerAPI.isEntityStacked(entidade);
    }
}
}