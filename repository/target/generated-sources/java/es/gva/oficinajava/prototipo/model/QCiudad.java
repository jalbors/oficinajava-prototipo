package es.gva.oficinajava.prototipo.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCiudad is a Querydsl query type for Ciudad
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCiudad extends EntityPathBase<Ciudad> {

    private static final long serialVersionUID = -1831802068L;

    public static final QCiudad ciudad = new QCiudad("ciudad");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nombre = createString("nombre");

    public final SetPath<Propietario, QPropietario> propietarios = this.<Propietario, QPropietario>createSet("propietarios", Propietario.class, QPropietario.class, PathInits.DIRECT2);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCiudad(String variable) {
        super(Ciudad.class, forVariable(variable));
    }

    public QCiudad(Path<? extends Ciudad> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCiudad(PathMetadata metadata) {
        super(Ciudad.class, metadata);
    }

}

