package io.ace.nordclient.utilz;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class HoleUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isBedrockHole(BlockPos pos) {
        boolean retVal = false;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK))
        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK))
        if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK))
        if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK))
        if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK))
        retVal = true;
        return retVal;

    }

    public static boolean isObiHole(BlockPos pos) {
        boolean retVal = false;
        int obiCount = 0;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
            if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
                if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR)) {
            if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                    obiCount++;
                }
                if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                    if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                        obiCount++;
                    }
                    if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                        if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                            obiCount++;
                        }
                        if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                            if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                                obiCount++;
                            }
                            if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                    obiCount++;
                                }
                                if (obiCount >= 1) {
                                    retVal = true;
                                }
                            }
                        }
                    }
                }
            }
                }
            }
        }
        return retVal;

    }

    public static boolean isDoubleHole(BlockPos pos) {
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
            if (!mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.AIR)) return false;
            if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.AIR)) {
                if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.AIR)) {
                    return false;
                }
                return !mc.world.getBlockState(pos.south().south()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.south().east()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.south().west()).getBlock().equals(Blocks.AIR);
            } else if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.AIR)) {
                if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.AIR)) {
                    return false;
                }
                return !mc.world.getBlockState(pos.east().east()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.east().north()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.east().south()).getBlock().equals(Blocks.AIR);
            } else if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.AIR)) {
                if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.AIR)) {
                    return false;
                }
                return !mc.world.getBlockState(pos.north().north()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.north().east()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.north().west()).getBlock().equals(Blocks.AIR);
            } else if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.AIR)) {
                if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.AIR)) {
                    return false;
                }
                return !mc.world.getBlockState(pos.south().south()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.south().east()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(pos.south().west()).getBlock().equals(Blocks.AIR);
            }

        }
        return true;
    }

    public static boolean isHole(BlockPos pos) {
        boolean retVal = false;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN))
        retVal = true;

        return retVal;
    }

    public static boolean isPlayerInHole() {
        boolean retVal = false;
        BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN))
        retVal = true;

        return retVal;
    }

    public static EnumFacing hasOneAir(BlockPos pos) {
        EnumFacing retVal = null;
        int airCount = 0;
        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.AIR)) {
            airCount++;
            retVal = EnumFacing.EAST;
        }
        if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.AIR)) {
            airCount++;
            retVal = EnumFacing.WEST;
        }
        if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.AIR)) {
            airCount++;
            retVal = EnumFacing.SOUTH;
        }
        if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.AIR)) {
            airCount++;
            retVal = EnumFacing.NORTH;
        }
        if (airCount != 1) {
            retVal = null;
        }
        return retVal;
    }

    public static boolean isBedrockEastHole(BlockPos pos) {
        boolean retVal = false;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
            if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
                if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR))
                    if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK))
                        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK))
                                if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK))
                                    if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK))
                                        retVal = true;
        return retVal;

    }

    public static boolean isBedrockWestHole(BlockPos pos) {
        boolean retVal = false;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
            if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
                if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR))
                    if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK))
                            if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK))
                                if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK))
                                    if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK))
                                        retVal = true;
        return retVal;

    }

    public static boolean isBedrockNorthHole(BlockPos pos) {
        boolean retVal = false;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
            if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
                if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR))
                    if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK))
                        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK))
                            if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK))
                                    if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK))
                                        retVal = true;
        return retVal;

    }

    public static boolean isBedrockSouthHole(BlockPos pos) {
        boolean retVal = false;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
            if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
                if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR))
                    if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK))
                        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK))
                            if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK))
                                if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK))
                                        retVal = true;
        return retVal;

    }

    public static boolean isObiNorthHole(BlockPos pos) {
        boolean retVal = false;
        int obiCount = 0;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
            if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
                if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR)) {
                    if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                        if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                            obiCount++;
                        }
                        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                            if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                                obiCount++;
                            }
                            if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                                if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                                    obiCount++;
                                }
                                    if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                        if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                            obiCount++;
                                        }
                                        if (obiCount >= 1) {
                                            retVal = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        return retVal;

    }

    public static boolean isObiSouthHole(BlockPos pos) {
        boolean retVal = false;
        int obiCount = 0;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
            if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
                if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR)) {
                    if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                        if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                            obiCount++;
                        }
                        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                            if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                                obiCount++;
                            }
                            if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                                if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                                    obiCount++;
                                }
                                if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                                    if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                                        obiCount++;
                                    }
                                        if (obiCount >= 1) {
                                            retVal = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        return retVal;

    }

    public static boolean isObiWestHole(BlockPos pos) {
        boolean retVal = false;
        int obiCount = 0;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
            if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
                if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR)) {
                    if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                        if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                            obiCount++;
                        }
                            if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                                if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                                    obiCount++;
                                }
                                if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                                    if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                                        obiCount++;
                                    }
                                    if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                        if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                            obiCount++;
                                        }
                                        if (obiCount >= 1) {
                                            retVal = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
        return retVal;

    }

    public static boolean isObiEastHole(BlockPos pos) {
        boolean retVal = false;
        int obiCount = 0;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
            if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
                if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR)) {
                    if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                        if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                            obiCount++;
                        }
                        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                            if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                                obiCount++;
                            }
                                if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                                    if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                                        obiCount++;
                                    }
                                    if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                        if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                            obiCount++;
                                        }
                                        if (obiCount >= 1) {
                                            retVal = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        return retVal;

    }
    public static boolean isTrapped(BlockPos pos) {
        if (!mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.AIR)) {
            if (!mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.AIR)) {
                if (!mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.AIR)) {
                    if (!mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.AIR)) {
                        if (!mc.world.getBlockState(pos.north().up()).getBlock().equals(Blocks.AIR)) {
                            if (!mc.world.getBlockState(pos.south().up()).getBlock().equals(Blocks.AIR)) {
                                if (!mc.world.getBlockState(pos.east().up()).getBlock().equals(Blocks.AIR)) {
                                    if (!mc.world.getBlockState(pos.west().up()).getBlock().equals(Blocks.AIR)) {
                                        return !mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        return false;

    }

}
