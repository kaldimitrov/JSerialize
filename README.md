# JSerialize

JSerialize is a robust Java library designed for efficient serialization, deserialization, and data compression. It simplifies the process of handling DAT and JSON files, transforming complex data types into a format that can be stored and retrieved easily. Its support for DAT file serialization enables developers to manage serialized data in DAT files effectively, while its JSON serialization capabilities allow for efficient handling of more complex data types, including maps and lists.

Moreover, JSerialize stands out with its innovative compression features. It supports multiple compression algorithms, giving developers the flexibility to choose the best fit for their needs. Notably, it includes a function that automatically determines the most effective compression algorithm for each object, optimizing the compression process. With these features, JSerialize provides a streamlined solution for developers working on data-intensive applications, making data management tasks more efficient and less cumbersome.

## Compression Algorithms

- **B2Zip Compression** - free and open-source file compression program that uses a combination of techniques like run-length encoding (RLE), the Burrows-Wheeler transform (BWT), move-to-front (MTF) transform, and Huffman coding. It is often used in big data applications with cluster computing frameworks like Hadoop and Apache Spark​1​.

- **LZMA Compression** - general-purpose compression algorithm with a high compression ratio and relatively slow compression speed. It's used predominantly in the 7z format in the 7-Zip program.

- **XZ Compression** - high compression ratio algorithm based on the LZMA2 algorithm. Its high compression ratio makes it suitable for software packages and distribution, backup and archiving. However, the tradeoff is slower compression and decompression speeds compared to some other algorithms.

- **LZ4 Compression** - renowned for providing extremely fast compression and decompression speeds, albeit at the expense of compression ratio. It's widely used in real-time applications such as in gaming, autonomous driving, and more where the speed of compression/decompression is crucial.

- **Snappy Compression** - developed by Google, prioritizes speed over compression ratio, making it ideal for use in scenarios where speed is critical. It's widely used in big data technologies like Hadoop and Cassandra for compressing/decompressing data quickly.

- **GZip Compression** - widely used compression algorithm that offers a good balance between compression ratio and speed. It's commonly used in web servers for compressing web content before it's sent to the client, reducing the amount of data that needs to be transmitted.

- **Deflate Compression** - lossless data compression algorithm that uses a combination of the LZ77 algorithm and Huffman coding. It's primarily used in the zlib library and gzip file format, both of which are standard data compression/decompression formats in Unix-like operating systems.

## Usage

### Creating an instance of the Serializer & Deserializer object

```java
Serializer serializer = new Serializer();
Deserializer deserializer = new Deserializer();
```

### Setting the serializer options

```java
serializer.setSerializer(new JsonFormat()); // Set the file format to serialize to
serializer.enableCompress(false); // Enable/disable compression
serializer.enableBestCompression(true); // When compression is enabled it uses the best algorithm for the object
serializer.setCompressor(new SnappyCompressor()); // Sets the compression algorithm to be used if bestCompression is disabled
```

### Setting the deserializer options

```java
serializer.setDeserializer(new JsonFormat()); // Set the file format to deserialize from
```

### Usage

```java
serializer.serialize(obj, fileName);

List<Object> objects = deserializer.deserialize(fileName);
```

## License

This project is licensed under the MIT License. This implies that you are free to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the software, given that you include the original copyright notice and the permission notice in all copies or substantial portions of the software. For more information, please see the [LICENSE](LICENSE) file in our project repository or visit the Open Source Initiative website.
