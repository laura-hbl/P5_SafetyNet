package com.safetynet.Alerts.unit.util;

import com.safetynet.Alerts.util.DataReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class DataReaderTest {

    private DataReader dataReader = new DataReader();

    @Test
    @Tag("Exception")
    @DisplayName("If filePath is empty, readFile should raise an FileNotFoundException")
    public void givenAnEmptyFilepath_whenReadFile_thenFileNotFoundExceptionIsThrown() {
        assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(() -> dataReader.readFile(""));
    }

    @Test
    @Tag("Exception")
    @DisplayName("If filePath is null, readFile should raise an NullPointerException")
    public void givenANullFilepath_whenReadFile_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> dataReader.readFile(null));
    }
}