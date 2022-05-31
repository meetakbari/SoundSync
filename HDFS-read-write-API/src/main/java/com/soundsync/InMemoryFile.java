package com.soundsync;

public class InMemoryFile {
    public String FileName = "";
    public String FileType = "";
    public byte[] FileContent;

    public InMemoryFile(String FileName, String FileType, byte[] FileContent) {
        this.FileName = FileName;
        this.FileContent = FileContent;
        this.FileType = FileType;
    }
}
