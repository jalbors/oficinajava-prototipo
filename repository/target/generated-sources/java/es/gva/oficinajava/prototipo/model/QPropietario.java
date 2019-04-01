package es.gva.oficinajava.prototipo.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPropietario is a Querydsl query type for Propietario
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPropietario extends EntityPathBase<Propietario> {

    private static final long serialVersionUID = 935061720L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPropietario propietario = new QPropietario("propietario");

    public final QPersona _super = new QPersona(this);

    //inherited
    public final StringPath apellidos = _super.apellidos;

    public final StringPath apodo = createString("apodo");

    public final QCiudad ciudad;

    public final StringPath direccion = createString("direccion");

    //inherited
    public final DatePath<java.util.Date> fechaNacimiento = _super.fechaNacimiento;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<Mascota, QMascota> mascotas = this.<Mascota, QMascota>createSet("mascotas", Mascota.class, QMascota.class, PathInits.DIRECT2);

    //inherited
    public final StringPath nombre = _super.nombre;

    public final StringPath telefono = createString("telefono");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QPropietario(String variable) {
        this(Propietario.class, forVariable(variable), INITS);
    }

    public QPropietario(Path<? extends Propietario> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPropietario(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPropietario(PathMetadata metadata, PathInits inits) {
        this(Propietario.class, metadata, inits);
    }

    public QPropietario(Class<? extends Propietario> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ciudad = inits.isInitialized("ciudad") ? new QCiudad(forProperty("ciudad")) : null;
    }

}

