package net.wesjd.anvilgui.version;

import org.bukkit.Bukkit;

/**
 * Matches the server's NMS version to its {@link VersionWrapper}
 *
 * @author Wesley Smith
 * @since 1.2.1
 */
public class VersionMatcher {

    /**
     * Matches the server version to it's {@link VersionWrapper}
     *
     * @return The {@link VersionWrapper} for this server
     * @throws IllegalStateException If the version wrapper failed to be instantiated or is unable to be found
     */
    public VersionWrapper match() {
        String craftBukkitPackage = Bukkit.getServer().getClass().getPackage().getName();

        String rVersion;
        if (!craftBukkitPackage.contains(".v")) { // cb package not relocated (i.e. paper 1.20.5+)
            rVersion = "Paper";
        } else {
            rVersion = craftBukkitPackage.split("\\.")[3].substring(1);
        }

        try {
            return (VersionWrapper) Class.forName(getClass().getPackage().getName() + ".Wrapper" + rVersion)
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("AnvilGUI does not support server version \"" + rVersion + "\"", exception);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to instantiate version wrapper for version " + rVersion, exception);
        }
    }
}
