package es.gva.oficinajava.prototipo.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersona is a Querydsl query type for Persona
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QPersona extends EntityPathBase<Persona> {

    private static final long serialVersionUID = 1879497560L;

    public static final QPersona persona = new QPersona("persona");

    public final StringPath apellidos = createString("apellidos");

    public final DatePath<java.util.Date> fechaNacimiento = createDate("fechaNacimiento", java.util.Date.class);

    public final StringPath nombre = createString("nombre");

    public QPersona(String variable) {
        super(Persona.class, forVariable(variable));
    }

    public QPersona(Path<? extends Persona> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersona(PathMetadata metadata) {
        super(Persona.class, metadata);
    }

}

