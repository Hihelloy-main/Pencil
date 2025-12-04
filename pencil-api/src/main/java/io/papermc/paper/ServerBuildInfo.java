package io.papermc.paper;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalInt;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.util.Services;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Information about the current Pencil server build.
 */
@NullMarked
@ApiStatus.NonExtendable
public interface ServerBuildInfo {
    /**
     * The brand id for Pencil.
     */
    Key BRAND_PENCIL_ID = Key.key("pencilmc", "pencil");

    /**
     * Gets the {@code ServerBuildInfo}.
     *
     * @return the {@code ServerBuildInfo}
     */
    static ServerBuildInfo buildInfo() {
        final class Holder {
            static final Optional<ServerBuildInfo> INSTANCE = Services.service(ServerBuildInfo.class);
        }
        return Holder.INSTANCE.orElseThrow();
    }

    Key brandId();
    @ApiStatus.Experimental
    boolean isBrandCompatible(final Key brandId);
    String brandName();
    String minecraftVersionId();
    String minecraftVersionName();
    OptionalInt buildNumber();
    Instant buildTime();
    Optional<String> gitBranch();
    Optional<String> gitCommit();
    String asString(final StringRepresentation representation);

    enum StringRepresentation {
        VERSION_SIMPLE,
        VERSION_FULL,
    }
}
