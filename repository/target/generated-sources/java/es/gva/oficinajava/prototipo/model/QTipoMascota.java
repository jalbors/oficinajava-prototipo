package es.gva.oficinajava.prototipo.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTipoMascota is a Querydsl query type for TipoMascota
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTipoMascota extends EntityPathBase<TipoMascota> {

    private static final long serialVersionUID = 1359865232L;

    public static final QTipoMascota tipoMascota = new QTipoMascota("tipoMascota");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nombre = createString("nombre");

    public QTipoMascota(String variable) {
        super(TipoMascota.class, forVariable(variable));
    }

    public QTipoMascota(Path<? extends TipoMascota> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTipoMascota(PathMetadata metadata) {
        super(TipoMascota.class, metadata);
    }

}

