package ca.antonious.viewcelladapter.viewcells;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by George on 2016-12-19.
 */

public abstract class GenericViewCellFactory<TModel, TViewCell extends GenericViewCell<?, TModel>> {
    public abstract TViewCell createViewCell(TModel model);

    public List<TViewCell> createAllViewCells(Collection<? extends TModel> models) {
        List<TViewCell> output = new ArrayList<>();

        for (TModel model: models) {
            output.add(createViewCell(model));
        }

        return output;
    }
}
