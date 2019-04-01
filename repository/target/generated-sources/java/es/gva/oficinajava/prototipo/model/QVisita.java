package es.gva.oficinajava.prototipo.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVisita is a Querydsl query type for Visita
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVisita extends EntityPathBase<Visita> {

    private static final long serialVersionUID = -1287902390L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVisita visita = new QVisita("visita");

    public final StringPath descripcion = createString("descripcion");

    public final EnumPath<EstadoVisita> estado = createEnum("estado", EstadoVisita.class);

    public final DatePath<java.util.Date> fecha = createDate("fecha", java.util.Date.class);

    public final DatePath<java.util.Date> fechaCierre = createDate("fechaCierre", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMascota mascota;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QVisita(String variable) {
        this(Visita.class, forVariable(variable), INITS);
    }

    public QVisita(Path<? extends Visita> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVisita(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVisita(PathMetadata metadata, PathInits inits) {
        this(Visita.class, metadata, inits);
    }

    public QVisita(Class<? extends Visita> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mascota = inits.isInitialized("mascota") ? new QMascota(forProperty("mascota"), inits.get("mascota")) : null;
    }

}

