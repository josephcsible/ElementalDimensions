package bitmovers.elementaldimensions.dimensions.generators;

import bitmovers.elementaldimensions.dimensions.generators.tools.FireTerrainGenerator;
import bitmovers.elementaldimensions.mobs.EntityBlaster;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import mcjty.lib.compat.CompatChunkGenerator;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.List;
import java.util.Random;

public class FireChunkGenerator implements CompatChunkGenerator {

    private final World worldObj;
    private Random random;
    private FireTerrainGenerator terraingen = new FireTerrainGenerator();

    private List<Biome.SpawnListEntry> mobs = Lists.newArrayList(new Biome.SpawnListEntry(EntityBlaster.class, 100, 2, 2));

    public FireChunkGenerator(World worldObj) {
        this.worldObj = worldObj;
        long seed = 0x1fff; // @todo
        this.random = new Random((seed + 314) * 516);
        terraingen.setup(worldObj, random, Blocks.NETHERRACK.getDefaultState(), Biomes.PLAINS);
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        ChunkPrimer chunkprimer = new ChunkPrimer();

        terraingen.generate(x, z, chunkprimer);

        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);

        byte[] biomeArray = chunk.getBiomeArray();
        for (int i = 0; i < biomeArray.length; ++i) {
            biomeArray[i] = (byte) Biome.getIdForBiome(Biomes.PLAINS);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {

    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        if (creatureType == EnumCreatureType.MONSTER){
            return mobs;
        }
        return ImmutableList.of();

    }

    @Override
    public BlockPos clGetStrongholdGen(World worldIn, String structureName, BlockPos position) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }
}
