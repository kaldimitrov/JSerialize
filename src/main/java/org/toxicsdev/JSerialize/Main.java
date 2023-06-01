package org.toxicsdev.JSerialize;

import lombok.SneakyThrows;
import org.toxicsdev.JSerialize.Utils.CompressorUtils;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        Person p = new Person();
        System.out.println(CompressorUtils.getBestCompressor(p));
    }
}