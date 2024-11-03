package xyz.acrylicstyle.gitupdater;

import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GitUpdater extends JavaPlugin {
    public final List<String> repositories = new ArrayList<>();

    @Override
    public void onEnable() {
        repositories.addAll(getConfig().getStringList("repositories"));
        Objects.requireNonNull(getCommand("gitupdater")).setExecutor(new GitUpdaterCommand(this));
    }

    public PullResult update(String repository) throws IOException, GitAPIException {
        try (Git git = Git.open(new File(repository))) {
            return git.pull().call();
        }
    }
}
