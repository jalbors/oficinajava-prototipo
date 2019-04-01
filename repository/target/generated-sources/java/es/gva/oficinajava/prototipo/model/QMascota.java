package es.gva.oficinajava.prototipo.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMascota is a Querydsl query type for Mascota
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMascota extends EntityPathBase<Mascota> {

    private static final long serialVersionUID = -897083036L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMascota mascota = new QMascota("mascota");

    public final StringPath apodo = createString("apodo");

    public final EnumPath<EstadoMascota> estado = createEnum("estado", EstadoMascota.class);

    public final DatePath<java.util.Date> fechaFallecimiento = createDate("fechaFallecimiento", java.util.Date.class);

    public final DatePath<java.util.Date> fechaNacimiento = createDate("fechaNacimiento", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nombre = createString("nombre");

    public final QPropietario propietario;

    public final QTipoMascota tipo;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final SetPath<Visita, QVisita> visitas = this.<Visita, QVisita>createSet("visitas", Visita.class, QVisita.class, PathInits.DIRECT2);

    public QMascota(String variable) {
        this(Mascota.class, forVariable(variable), INITS);
    }

    public QMascota(Path<? extends Mascota> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMascota(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMascota(PathMetadata metadata, PathInits inits) {
        this(Mascota.class, metadata, inits);
    }

    public QMascota(Class<? extends Mascota> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.propietario = inits.isInitialized("propietario") ? new QPropietario(forProperty("propietario"), inits.get("propietario")) : null;
        this.tipo = inits.isInitialized("tipo") ? new QTipoMascota(forProperty("tipo")) : null;
    }

}

