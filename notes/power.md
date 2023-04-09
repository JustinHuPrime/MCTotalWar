# Power

Power in Total War is electric power, distributed like the real-world electric grid, and like the real world, it is hard to store.

For each network, the producers produce some amount of power each tick. This power is distributed equally amongst all consumers.

## Transformers

A step down transformer acts as a consumer for one face, and a producer for the rest. The input power is distributed equally the next tick among the connected output faces. A step up transformer acts as a producer on one face, and a consumer for the rest. The input power is consolidated and produced the next tick on the output face. Transformers are rated for some power range.

## Metering

A power meter is rated for some power range, and reports how much power is flowing as an analog redstone signal read by comparator. Full power indicates the maximum power for the tier is flowing.

## Switching

A power switch is rated for some power range, and conditionally connects two networks.
