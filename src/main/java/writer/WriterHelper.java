package writer;

class WriterHelper {

    String setupWikiFilePath(String inputDirectory, String fileNamePath, String outputPath) {
        String xmlFileName = extractFileName(inputDirectory, fileNamePath);
        String wikiName = changeFileExtension(xmlFileName);

        return outputPath + wikiName;
    }

    String changeFileExtension(String fileName) {
        if (fileName.isEmpty())
            throw new InvalidFileNameException("Invalid file name:[" + fileName + "]");

        String[] tokens = fileName.split("\\.(?=[^.]+$)");

        String base = tokens[0];
        String newExtension = ".wiki";

        return base + newExtension;
    }

    String extractFileName(String inputDirectoryPath, String filePath) {
        int i = inputDirectoryPath.length();

        String name = filePath.substring(i);
        name = name.replace("\\", "");

        return name;
    }

    class InvalidFileNameException extends RuntimeException {
        InvalidFileNameException(String message) {
            super(message);
        }
    }

}
