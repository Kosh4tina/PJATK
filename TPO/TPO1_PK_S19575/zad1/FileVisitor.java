package zad1;

import static java.nio.file.FileVisitResult.*;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.EnumSet;

class FileVisitor extends SimpleFileVisitor<Path> {

    private final FileChannel output_file_channel;

    Charset inCharset  = Charset.forName("Cp1250"),
            outCharset = StandardCharsets.UTF_8;

    public FileVisitor(Path output_file_path) throws IOException {
        this.output_file_channel = FileChannel.open(output_file_path, EnumSet.of(CREATE, APPEND));
    }

    private void recode(FileChannel input_file_channel, long buffer_size) throws IOException {
        ByteBuffer common_buffer = ByteBuffer.allocate((int) buffer_size + 1);
        common_buffer.clear();
            input_file_channel.read(common_buffer);
            common_buffer.flip();
            CharBuffer c_buff = inCharset.decode(common_buffer);
            ByteBuffer buf = outCharset.encode(c_buff);
            while( buf.hasRemaining() ){
                this.output_file_channel.write(buf);
            }
    }

    @Override
    public FileVisitResult visitFile(Path file_path, BasicFileAttributes attr) throws IOException {
        System.out.println(file_path);
        this.recode(FileChannel.open(file_path), attr.size());
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file_path, IOException exc) {
        System.err.println("FileVisitor.visitFileFailed: " + exc);
        return CONTINUE;
    }
}