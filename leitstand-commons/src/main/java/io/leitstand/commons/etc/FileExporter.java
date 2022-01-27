package io.leitstand.commons.etc;

import java.io.IOException;
import java.io.Writer;

/**
 * A file exporter to write content to a file.
 */
@FunctionalInterface
public interface FileExporter {

    /**
     * Writes the file content.
     * @param w the file writer
     * @throws IOException
     */
    void write(Writer w) throws IOException;

}