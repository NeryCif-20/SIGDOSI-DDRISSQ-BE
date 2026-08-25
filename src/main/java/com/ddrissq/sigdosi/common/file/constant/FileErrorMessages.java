package com.ddrissq.sigdosi.common.file.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileErrorMessages {

    public static final String FILE_STORAGE_INITIALIZATION_FAILED = "No fue posible inicializar el almacenamiento de archivos";
    public static final String FILE_STORAGE_FAILED = "No fue posible almacenar el archivo";
    public static final String FILE_NOT_FOUND = "No existe el archivo solicitado";
    public static final String FILE_DELETE_FAILED = "No fue posible eliminar el archivo";
    public static final String FILE_LOAD_FAILED = "No fue posible cargar el archivo";

}
