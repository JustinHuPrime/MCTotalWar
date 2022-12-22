# Power

Power in Total War is electric power, distributed like the real-world electric grid. It is hard to
store.

## Power Generation

There are three main power generators:

- steam turbines; these take five minutes to throttle and are 50% efficient at turning steam
   into power.
- gas turbines; these take 30 seconds to throttle and are 40% efficient at turning fuel heat
   into power.
- engine generators; these take 5 seconds to throttle and are 30% efficient at turning fuel heat
   into power.

There are three secondary power generators:

- solar; these produce power only during the day, and are 20% efficient at turning sunlight into
   power
- wind; these produce power at irregular times, and are 50% efficient at turning wind energy into
   power
- tidal; these produce power at sunrise and sunset, and are 80% efficient at turning wave energy
   into power

### Steam Boiling

Steam is produced in one of three ways:

- through fossil fuel boilers; these are 80% efficient at turning fuel heat into steam
- through nuclear fission; these are 60% efficient at turning fuel heat into steam
- through nuclear fusion; these are 50% efficient at turning fuel heat into steam

## Power Storage

There are three power storage systems:

- pumped hydro storage; these take 5 seconds to respond to demand and are 80% efficient round-trip
- battery banks; these respond instantly to demand and are 80% efficient round-trip, but are bulky
- capacitors; these respond instantly to demand and are 90% efficient round-trip, but are even more
   bulky

Power storage is, depending on redstone signal, either a producer or a consumer.

## Power Distribution

For each network, the producers produce some amount of power each tick. This power is distributed
equally amongst all consumers.

### Power Tiers

Each power tier has a lower power limit and an upper power limit. If not enough power is supplied to
a machine, it's consumed without progressing the recipe. If too much power is supplied, the machine
also stops progressing the recipe, and catches fire. Distribution cables and equipment also respect
the upper power limit, and will catch fire and stop working if above the power limit.

The following power tiers exist:

- Low power
- Medium power
- High power
- Extreme power

### Transformers

A step-up transformer acts as a consumer for one face, and a producer for the rest. The input power
is distributed equally the next tick among the connected output faces. A step down transformer acts
as a producer on one face, and a consumer for the rest. The input power is consolidated and produced
the next tick on the output face. Transformers are rated for some power range.

### Metering

A power meter is rated for some power range, and reports how much power is flowing as an analog
redstone signal read by comparator. Full power indicates the maximum power for the tier is flowing.

### Switching

A power switch is rated for some power range, and conditionally connects two networks.
