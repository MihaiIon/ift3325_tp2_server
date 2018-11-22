package factories;

import models.TypeModel;
import static models.TypeModel.Type;

public class TypeFactory {

    /**
     * Provides a TypeModel corresponding to the provided <type>.
     */
    public static TypeModel createTypeModel(Type type) {
        switch (type) {
            case INFORMATION:
                return new TypeModel(type, 'I');
            case CONNECTION_REQUEST:
                return new TypeModel(type, 'C');
            case FRAME_RECEPTION:
                return new TypeModel(type, 'A');
            case REJECTED_FRAME:
                return new TypeModel(type, 'R');
            case TERMINATE_CONNECTION_REQUEST:
                return new TypeModel(type, 'F');
            default:
                return new TypeModel(type, 'P');
        }
    }
}