package io.papermc.paper;

import com.google.common.base.Strings;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.jar.Manifest;
import io.papermc.paper.util.JarManifests;
import net.kyori.adventure.key.Key;
import net.minecraft.SharedConstants;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.Main;
import org.jetbrains.annotations.NotNull;

public record ServerBuildInfoImpl(
    Key brandId,
    String brandName,
    String minecraftVersionId,
    String minecraftVersionName,
    OptionalInt buildNumber,
    Instant buildTime,
    Optional<String> gitBranch,
    Optional<String> gitCommit
) implements ServerBuildInfo {

    private static final String ATTRIBUTE_BRAND_ID = "Brand-Id";
    private static final String ATTRIBUTE_BRAND_NAME = "Brand-Name";
    private static final String ATTRIBUTE_BUILD_TIME = "Build-Time";
    private static final String ATTRIBUTE_BUILD_NUMBER = "Build-Number";
    private static final String ATTRIBUTE_GIT_BRANCH = "Git-Branch";
    private static final String ATTRIBUTE_GIT_COMMIT = "Git-Commit";

    /** Pencil brand name */
    private static final String BRAND_PENCIL_NAME = "Pencil";

    /** Pencil brand key */
    private static final Key BRAND_PENCIL_ID = Key.key("pencil", "server");

    private static final String BUILD_DEV = "DEV";

    public ServerBuildInfoImpl() {
        this(JarManifests.manifest(CraftServer.class));
    }

    private ServerBuildInfoImpl(final Manifest manifest) {
        this(
            BRAND_PENCIL_ID,
            BRAND_PENCIL_NAME,
            SharedConstants.getCurrentVersion().id(),
            SharedConstants.getCurrentVersion().name(),
            getManifestAttribute(manifest, ATTRIBUTE_BUILD_NUMBER)
                .map(Integer::parseInt)
                .map(OptionalInt::of)
                .orElse(OptionalInt.empty()),
            getManifestAttribute(manifest, ATTRIBUTE_BUILD_TIME)
                .map(Instant::parse)
                .orElse(Main.BOOT_TIME),
            getManifestAttribute(manifest, ATTRIBUTE_GIT_BRANCH),
            getManifestAttribute(manifest, ATTRIBUTE_GIT_COMMIT)
        );
    }

    @Override
    public boolean isBrandCompatible(final @NotNull Key brandId) {
        return brandId.equals(this.brandId);
    }

    @Override
    public @NotNull String asString(final @NotNull StringRepresentation representation) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.minecraftVersionId);
        sb.append('-');
        if (this.buildNumber.isPresent()) {
            sb.append(this.buildNumber.getAsInt());
        } else {
            sb.append(BUILD_DEV);
        }
        final boolean hasGitBranch = this.gitBranch.isPresent();
        final boolean hasGitCommit = this.gitCommit.isPresent();
        if (hasGitBranch || hasGitCommit) {
            sb.append('-');
        }
        if (hasGitBranch && representation == StringRepresentation.VERSION_FULL) {
            sb.append(this.gitBranch.get());
            if (hasGitCommit) {
                sb.append('@');
            }
        }
        if (hasGitCommit) {
            sb.append(this.gitCommit.get());
        }
        if (representation == StringRepresentation.VERSION_FULL) {
            sb.append(' ');
            sb.append('(');
            sb.append(this.buildTime.truncatedTo(ChronoUnit.SECONDS));
            sb.append(')');
        }
        return sb.toString();
    }

    private static Optional<String> getManifestAttribute(final Manifest manifest, final String name) {
        final String value = manifest != null ? manifest.getMainAttributes().getValue(name) : null;
        return Optional.ofNullable(Strings.emptyToNull(value));
    }
}
