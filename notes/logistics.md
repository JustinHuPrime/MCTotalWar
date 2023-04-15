# Logistics

For item, fluid networks:

Have three kinds of components: input components, output components, and storage components.

May only take from output and storage components, may only input to input and storage components. Try to draw from output components first, and insert into input components first.

Networks don't filter; machines themselves do filter which items are allowed in (via slot-locking) and which fluids are allowed in (also slot-locking)

No output filters!

Implication -> network transporters are very cheap tick-wise.

Also, instant transfer

## Item Networks

For item networks, network leader queries network about which items are available and which items are desired (and which are stored, if there's unmatched consumers or producers).

Items are transferred (in arbitrary order) from consumers to producers up to network transfer limit (starts at 16 items/tick, upgrades in multiples of 4 to 1024 items/tick, then SC tier item transport has no limit)

## Fluid Networks

For fluid networks, network starts out untyped. When untyped, network queries for type of fluid output, input, or storage, and only acquires a type if one fluid only is transferred.

Once network has a type, queries for producers and consumers (and storages, if there's an imbalance), and transfers fluids to balance (starts at 16 buckets/tick, upgrades in multiples of 4 to 1024 buckets/tick, then SC tier fluid transport has no limit)

Fluid networks don't lose fluid type (how to implement this - have non-IO components remember which fluid type?)
