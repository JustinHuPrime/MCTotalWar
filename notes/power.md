# Power

Power in Total War is electric power, distributed like the real-world electric grid, and like the real world, it is hard to store.

For each network, the producers produce some amount of power each tick. This power is distributed equally amongst all consumers.

## Transformers

A step down transformer acts as a consumer for one face, and a producer for the rest. The input power is distributed equally the next tick among the connected output faces. A step up transformer acts as a producer on one face, and a consumer for the rest. The input power is consolidated and produced the next tick on the output face. Transformers are rated for some power range.

## Metering

A power meter is rated for some power range, and reports how much power is flowing as an analog redstone signal read by comparator. Full power indicates the maximum power for the tier is flowing.

## Switching

A power switch is rated for some power range, and conditionally connects two networks.

## Cables

Cables in Total War can be made of copper, aluminium, or superconductor. A cable has a specific voltage it's able to support, if used beyond which it will start fires and refuse to carry energy.

Copper cables can only be manufactured up to the MV tier

Aluminium cables can go up to EV

Bundle four lower-tier cables together to get a higher tier cable

Superconductor cables can handle up to SV, but have no lower voltage tier variants
