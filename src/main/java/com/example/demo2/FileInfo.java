package com.example.demo2;

import javafx.beans.property.SimpleStringProperty;

// this class is used to store information of files in TableView
public class FileInfo{
    private final SimpleStringProperty fileName;
    private final SimpleStringProperty file_category;
    private final SimpleStringProperty date_created;
    private final SimpleStringProperty file_extension;

    public FileInfo(String fileName, String file_category, String date_created, String file_extension) {
        this.fileName = new SimpleStringProperty(fileName);
        this.file_category = new SimpleStringProperty(file_category);
        this.date_created = new SimpleStringProperty(date_created);
        this.file_extension = new SimpleStringProperty(file_extension);
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getFile_category() {
        return file_category.get();
    }

    public void setFile_category(String file_category) {
        this.file_category.set(file_category);
    }

    public String getDate_created() {
        return date_created.get();
    }

    public void setDate_created(String date_created) {
        this.date_created.set(date_created);
    }

    public String getFile_extension() {
        return file_extension.get();
    }

    public void setFile_extension(String file_extension) {
        this.file_extension.set(file_extension);
    }

}
