package com.sushantsundi.n3xPAL;

import java.util.*;
import java.util.regex.*;

final class FileEntry {
    final String name;
    final boolean isDir;
    final long size;

    FileEntry(String name, boolean isDir, long size) {
        this.name = name; this.isDir = isDir; this.size = size;
    }

    String sizeLabel() {
        if (isDir) return "";
        if (size < 1024) return size + " B";
        if (size < 1024 * 1024) return String.format(Locale.ROOT, "%.1f KB", size / 1024f);
        if (size < 1024L * 1024 * 1024) return String.format(Locale.ROOT, "%.1f MB", size / (1024f * 1024));
        return String.format(Locale.ROOT, "%.1f GB", size / (1024f * 1024 * 1024));
    }

    static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format(Locale.ROOT, "%.1f KB", bytes / 1024f);
        return String.format(Locale.ROOT, "%.1f MB", bytes / (1024f * 1024));
    }

    // Parses output of: ls -la <path>
    // Handles both Android formats:
    //   -rw-r--r-- 1 root root 1234 2024-01-01 12:00 name
    //   -rw-rw---- root sdcard_rw 1234 2024-01-01 12:00 name
    // Correctly captures filenames with spaces by anchoring on the date field.
    static List<FileEntry> parseList(String raw) {
        List<FileEntry> list = new ArrayList<>();
        // Matches ISO date+time preceding the filename
        Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}\\s+");
        for (String line : raw.split("\n")) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("total ")) continue;
            if (line.length() < 2) continue;
            char type = line.charAt(0);
            if (type != '-' && type != 'd' && type != 'l') continue;
            boolean isDir = type == 'd';
            Matcher m = datePattern.matcher(line);
            if (!m.find()) continue;
            String name = line.substring(m.end()).trim();
            if (name.isEmpty() || name.equals(".") || name.equals("..")) continue;
            // Strip symlink target if present (name -> target)
            int arrow = name.indexOf(" -> ");
            if (arrow >= 0) { isDir = true; name = name.substring(0, arrow); }
            // Find size: last numeric field before the date
            long size = 0;
            String before = line.substring(0, m.start()).trim();
            String[] parts = before.split("\\s+");
            for (int i = parts.length - 1; i >= 0; i--) {
                try { size = Long.parseLong(parts[i]); break; }
                catch (NumberFormatException ignored) {}
            }
            list.add(new FileEntry(name, isDir, size));
        }
        list.sort((a, b) -> {
            if (a.isDir != b.isDir) return a.isDir ? -1 : 1;
            return a.name.compareToIgnoreCase(b.name);
        });
        return list;
    }
}
